/*
 * Copyright (c) 2018 - Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *
 */

package de.gishmo.gwtbootstarternalu.server.resource.generator.impl;

import com.google.gwt.user.client.ui.Widget;
import com.squareup.javapoet.ClassName;
import de.gishmo.gwt.gwtbootstarternalu.shared.model.NaluGeneraterParms;
import elemental2.dom.HTMLElement;

import java.io.File;

public abstract class AbstractSourceGenerator {

  protected NaluGeneraterParms naluGeneraterParms;
  protected File               directoryJava;
  protected String             clientPackageJavaConform;


  protected ClassName getClassNameWidget() {
    switch (this.naluGeneraterParms.getWidgetLibrary()) {
      case DOMINO_UI:
      case ELEMENTO:
        return ClassName.get(HTMLElement.class);
      case GWT:
      case GXT:
        return ClassName.get(Widget.class);
      default:
        return ClassName.get(Widget.class);
    }
  }
}
