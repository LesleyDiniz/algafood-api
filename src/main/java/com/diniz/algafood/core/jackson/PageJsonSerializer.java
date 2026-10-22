package com.diniz.algafood.core.jackson;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>> {

	@Override
	public void serialize(Page<?> page, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeStartObject();
		gen.writeNumberField("totalElements", page.getTotalElements());
		gen.writeNumberField("totalPages", page.getTotalPages());
		gen.writeNumberField("size", page.getSize());
		gen.writeNumberField("number", page.getNumber());
		gen.writeBooleanField("first", page.isFirst());
		gen.writeBooleanField("last", page.isLast());
		gen.writeArrayFieldStart("content");
		for (Object content : page.getContent()) {
			serializers.defaultSerializeValue(content, gen);
		}
		gen.writeEndArray();
		gen.writeEndObject();
	}

}
