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

import java.util.logging.Logger;

public class MeiPlugin extends CustomPlugin {
    private Logger log;

    @Override
    public void onEnable() {
        log = getLogger();
        log.info("MUI plugin enabled!");

        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();

        // getting backend to init it. We could remove this after debuging
        getBackend();

        EventProcessor processor = new EventProcessor(this);
        processor.runTaskTimer(this, 20, 20 * getConfig().getInt("interval", 60));
    }

    @Override
    public void onDisable() {
        getBackend().closeBackend();
        log.info("MUI plugin disabled!");
    }

}
