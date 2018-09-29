/*
 *    Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package de.kaiserpfalzedv.accounts.templating;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.output.StringBuilderWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.Writer;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-09-10
 */
@Named
public class YamlPrinter {
    private static final Logger LOG = LoggerFactory.getLogger(YamlPrinter.class);

    @Inject
    private Configuration configuration;

    private String templatePath = "/templates/";


    public String print(
            @NotNull final Object data,
            @NotNull final String templateName
    ) throws IOException, TemplateException {
        configuration.setClassForTemplateLoading(this.getClass(), templatePath);
        
        Template template = configuration.getTemplate(templateName);

        Writer out = new StringBuilderWriter();
        template.process(data, out);
        return out.toString();
    }
}
