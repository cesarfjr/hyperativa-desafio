package br.com.cesarfjr.hyperativa.backend.base.audit;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.BodyFilter;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Origin;
import org.zalando.logbook.RequestFilter;
import org.zalando.logbook.json.JsonHttpLogFormatter;
import org.zalando.logbook.logstash.LogstashLogbackSink;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.cesarfjr.hyperativa.backend.cartao.util.MascaraUtil;

@Configuration
public class LogbookConfig {

	@Bean
	public Logbook logbook() {
		RequestFilter requestFilter = originalRequest -> new HttpRequest() {
			@Override
			public String getPath() {
				String originalPath = originalRequest.getPath();
				// Check if the path matches the specific pattern
				if (originalPath.matches(".*/cartoes/[^/]+")) {
					Pattern pattern = Pattern.compile("(.*?/cartoes/)([^/]+)");
					Matcher matcher = pattern.matcher(originalPath);

					StringBuffer result = new StringBuffer();
					while (matcher.find()) {
						String sensitiveValue = matcher.group(2); // Extract the sensitive value
						String truncatedValue = sensitiveValue.length() > 10 ? MascaraUtil.mascaraPan(sensitiveValue)
								: sensitiveValue; // Truncate if longer than 10 characters

						matcher.appendReplacement(result, matcher.group(1) + truncatedValue);
					}
					matcher.appendTail(result);
					return result.toString();
				}
				return originalPath; // Return unchanged for paths not matching the pattern
			}

			@Override
			public String getProtocolVersion() {
				return originalRequest.getProtocolVersion();
			}

			@Override
			public String getMethod() {
				return originalRequest.getMethod();
			}

			@Override
			public String getScheme() {
				return originalRequest.getScheme();
			}

			@Override
			public String getHost() {
				return originalRequest.getHost();
			}

			@Override
			public String getQuery() {
				return originalRequest.getQuery();
			}

			@Override
			public String getContentType() {
				return originalRequest.getContentType();
			}

			@Override
			public String getRemote() {
				return originalRequest.getRemote();
			}

			@Override
			public byte[] getBody() throws IOException {
				return originalRequest.getBody();
			}

			@Override
			public HttpRequest withBody() throws IOException {
				return originalRequest.withBody();
			}

			@Override
			public Origin getOrigin() {
				return originalRequest.getOrigin();
			}

			@Override
			public HttpHeaders getHeaders() {
				return originalRequest.getHeaders();
			}

			@Override
			public Optional<Integer> getPort() {
				return originalRequest.getPort();
			}

			@Override
			public HttpRequest withoutBody() {
				return originalRequest.withoutBody();
			}

		};

		BodyFilter bodyFilter = (contentType, body) -> {
			if (contentType != null && contentType.contains("application/json")) {
				Pattern pattern = Pattern.compile("\"pan\"\\s*:\\s*\"(.*?)\"");
				Matcher matcher = pattern.matcher(body);

				StringBuffer result = new StringBuffer();
				while (matcher.find()) {
					String sensitiveValue = matcher.group(1); // Extract the sensitive value
					String maskedValue = sensitiveValue.length() > 10 ? MascaraUtil.mascaraPan(sensitiveValue)
							: sensitiveValue; // Mask if longer than 10 characters

					matcher.appendReplacement(result, "\"pan\":\"" + maskedValue + "\"");
				}
				matcher.appendTail(result);

				return result.toString();
			}
			return body; // Return unchanged for non-JSON bodies
		};
		// Create an ObjectMapper with pretty-printing enabled
		ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

		// Define a custom HttpLogFormatter for pretty JSON
		HttpLogFormatter formatter = new JsonHttpLogFormatter(objectMapper);
		LogstashLogbackSink logstashsink = new LogstashLogbackSink(formatter);
		Logbook logbook = Logbook.builder().bodyFilter(bodyFilter).requestFilter(requestFilter).sink(logstashsink)
				.build();
		return logbook;
	}

}
