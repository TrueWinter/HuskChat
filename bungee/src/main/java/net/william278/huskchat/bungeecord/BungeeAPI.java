/*
 * This file is part of HuskChat, licensed under the Apache License 2.0.
 *
 *  Copyright (c) William278 <will27528@gmail.com>
 *  Copyright (c) contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package net.william278.huskchat.bungeecord;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.william278.huskchat.API;
import net.william278.huskchat.HuskChat;
import net.william278.huskchat.api.player.Player;
import net.william278.huskchat.bungeecord.player.BungeePlayer;
import org.jetbrains.annotations.NotNull;

public class BungeeAPI extends API {
    public BungeeAPI(HuskChat huskChat) {
        super(huskChat);
    }

    @Override
    public Player adaptPlayer(@NotNull Object player) throws IllegalArgumentException {
        if (!(player instanceof ProxiedPlayer)) {
            throw new IllegalArgumentException("Player must be a Bungee ProxiedPlayer");
        }

        return BungeePlayer.adapt((ProxiedPlayer) player);
    }
}
