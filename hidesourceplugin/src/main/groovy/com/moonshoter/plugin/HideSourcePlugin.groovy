package com.moonshoter.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class HideSourcePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        final android = project.extensions.android

        HideSourceTask sourcesTask = project.tasks.findByName("hideSourceJar") as HideSourceTask
        if (sourcesTask == null) {
            sourcesTask = project.tasks.create("hideSourceJar", HideSourceTask)
        }
        sourcesTask.from(android.sourceSets.main.java.srcDirs)

    }


}