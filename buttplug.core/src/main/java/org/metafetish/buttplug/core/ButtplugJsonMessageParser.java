package org.metafetish.buttplug.core;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;

public class ButtplugJsonMessageParser {

	private final ObjectMapper mapper;

	public ButtplugJsonMessageParser() {
		this.mapper = new ObjectMapper();
		TypeResolverBuilder<?> typer = new DefaultTypeResolverBuilder(DefaultTyping.OBJECT_AND_NON_CONCRETE);
		typer = typer.init(JsonTypeInfo.Id.NAME, null);
		typer = typer.inclusion(As.WRAPPER_OBJECT);
		this.mapper.setDefaultTyping(typer);
	}

	public List<ButtplugMessage> parseJson(final String json) throws IOException {
		return Arrays.asList(this.mapper.readValue(json, ButtplugMessage[].class));
	}

	public String formatJson(final List<ButtplugMessage> msgs) throws IOException {
		return this.mapper.writeValueAsString(msgs);
	}

	public String formatJson(final ButtplugMessage msgs) throws IOException {
		return this.mapper.writeValueAsString(new ButtplugMessage[] { msgs });
	}
}
