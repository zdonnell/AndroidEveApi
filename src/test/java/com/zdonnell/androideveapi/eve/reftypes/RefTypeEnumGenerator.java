package com.zdonnell.androideveapi.eve.reftypes;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import com.zdonnell.androideveapi.eve.reftypes.ApiRefType;
import com.zdonnell.androideveapi.eve.reftypes.RefTypesParser;
import com.zdonnell.androideveapi.eve.reftypes.RefTypesResponse;
import com.zdonnell.androideveapi.exception.ApiException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class RefTypeEnumGenerator {

	public static void main(String[] args) throws IOException, TemplateException, ApiException {
		RefTypesParser parser = RefTypesParser.getInstance();
		RefTypesResponse response = parser.getResponse();
		Collection<ApiRefType> refTypes = response.getAll();
		Configuration cfg = new Configuration();
		Template tpl = cfg.getTemplate("src/test/resources/refType.ftl");
		FileWriter output = new FileWriter("src/test/java/com/beimin/eveapi/shared/wallet/RefType.java");
		HashMap<String, Object> model = new HashMap<String, Object>();
		model.put("refTypes", refTypes);
		tpl.process(model, output);
		output.flush();
		output.close();
	}
}