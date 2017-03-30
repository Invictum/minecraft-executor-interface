/*
 * Copyright 2013 Moritz Hilscher
 *
 * This file is part of Minecraft Executor Interface (aka MEI).
 *
 * MEI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MEI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MEI.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.invictum.mei;

import com.github.invictum.mei.schedule.ScheduleFacility;
import org.bukkit.Bukkit;

import java.util.logging.Logger;

public class MeiPlugin extends CustomPlugin {
    private Logger log;

    @Override
    public void onEnable() {
        log = getLogger();

        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();

        if (!getBackend().initBackend()) {
            log.warning("MUI plugin can't establish connect to backend, so stopping plugin. Please, check MEI config file.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        ScheduleFacility.start();
    }

    @Override
    public void onDisable() {
        ScheduleFacility.shutdown();
        getBackend().closeBackend();
    }
}
