/*
 * Copyright (c) 2018 - 2020 - Frank Hossfeld
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

package com.github.nalukit.bootstarternalu.client;

import com.github.nalukit.nalu.plugin.elemental2.client.NaluPluginElemental2;
import com.google.gwt.core.client.EntryPoint;
import org.dominokit.domino.rest.DominoRestConfig;
import org.dominokit.domino.ui.forms.DominoFields;
import org.dominokit.domino.ui.forms.FieldStyle;

public class App
    implements EntryPoint {

  public void onModuleLoad() {
    // initialize domino rest
    DominoRestConfig.initDefaults();
    // remove default buttom margin from fields
    DominoFields.INSTANCE.setDefaultFieldsStyle(FieldStyle.ROUNDED);
    BootStarterNaluApplication application = new BootStarterNaluApplicationImpl();
    application.run(new NaluPluginElemental2());
  }

}