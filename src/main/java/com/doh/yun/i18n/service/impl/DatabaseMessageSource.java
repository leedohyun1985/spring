package com.doh.yun.i18n.service.impl;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;

import com.doh.yun.i18n.mapper.I18nMapper;

public class DatabaseMessageSource extends AbstractMessageSource {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private Messages messages;

	@Autowired
	I18nMapper mapper;

	@Override
	protected MessageFormat resolveCode(String code, Locale locale) {
		String message = messages.getMessage(code, locale);
		return createMessageFormat(message, locale);
	}

	@PostConstruct
	private void init() {
		Messages messages = new Messages();

		List<Map<String, String>> messageMapList = mapper.selectAllMessages();
		for (Map<String, String> map : messageMapList) {
			
			Locale locale = new Locale(map.get("language"), map.get("country").toUpperCase());
			messages.addMessage(map.get("code"), locale, map.get("message"));
		}
		this.messages = messages;
	}

	protected class Messages {

		private Map<String, Map<Locale, String>> messages;

		public void addMessage(String code, Locale locale, String message) {
			if (null == messages) {
				messages = new HashMap<String, Map<Locale, String>>();
			}

			Map<Locale, String> dataMap = messages.get(code);
			if (null == dataMap) {
				dataMap = new HashMap<Locale, String>();
				messages.put(code, dataMap);
			}
			dataMap.put(locale, message);

		}

		public String getMessage(String code, Locale locale) {
			Map<Locale, String> dataMap = messages.get(code);

			if (null == dataMap) {
				init();
				return dataMap != null ? dataMap.get(locale) : "";
			}
			return dataMap.get(locale);
		}

	}
}
