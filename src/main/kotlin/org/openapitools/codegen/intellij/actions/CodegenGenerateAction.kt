/*
 * Copyright 2016 Jim Schubert
 * Copyright 2018 OpenAPI-Generator Contributors (https://openapi-generator.tech)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.openapitools.codegen.intellij.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import org.openapitools.codegen.intellij.events.GenerationNotificationManager
import org.openapitools.codegen.intellij.ui.GenerateDialog

class CodegenGenerateAction : AnAction() {

    private val notificationManager: GenerationNotificationManager = GenerationNotificationManager()

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val file = e.getData(PlatformDataKeys.VIRTUAL_FILE) ?: return

        GenerateDialog(project, file, notificationManager).show()
    }

    override fun update(e: AnActionEvent?) {
        try {
            val file = e?.getData(PlatformDataKeys.VIRTUAL_FILE)
            val ext = file?.extension?.toLowerCase() ?: ""
            if (e != null && file != null) {
                // TODO: Add a better condition here, or maybe support user-defined regex
                e.presentation.isEnabled = validExtensions.contains(ext)
            } else {
                super.update(e)
            }
        } catch(ex: Throwable) {
            super.update(e)
        }
    }

    companion object {
        val validExtensions = listOf("yaml", "yml", "json")
    }
}