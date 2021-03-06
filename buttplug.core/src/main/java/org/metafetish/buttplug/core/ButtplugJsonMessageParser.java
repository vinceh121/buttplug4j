package org.metafetish.buttplug.core;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;

public class ButtplugJsonMessageParser {

	private final ObjectMapper mapper;

	public ButtplugJsonMessageParser() {
		this.mapper = new ObjectMapper();
		TypeResolverBuilder<?> typer = DefaultTypeResolverBuilder.construct(DefaultTyping.JAVA_LANG_OBJECT,
				this.mapper.getPolymorphicTypeValidator());
		// reassignment here because documentation says "@returns Resulting builder
		// instance (usually this builder, but not necessarily)"
		typer = typer.init(JsonTypeInfo.Id.NAME, null);
		typer = typer.inclusion(As.WRAPPER_OBJECT);
		this.mapper.setDefaultTyping(typer);
	}

	public List<ButtplugMessage> parseJson(final String json) throws JsonProcessingException {
		return this.mapper.readValue(json, new TypeReference<List<ButtplugMessage>>() {});
	}

	public String formatJson(final ButtplugMessage msgs) throws JsonProcessingException {
		return this.formatJson(Collections.singletonList(msgs));
	}

	public String formatJson(final List<ButtplugMessage> msgs) throws JsonProcessingException {
		return this.mapper.writeValueAsString(msgs);
	}

	public ObjectMapper getMapper() {
		return mapper;
	}
}
