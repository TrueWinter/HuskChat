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

package net.william278.huskchat.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import net.william278.huskchat.command.*;
import net.william278.huskchat.player.ConsolePlayer;
import net.william278.huskchat.velocity.VelocityHuskChat;
import net.william278.huskchat.velocity.player.VelocityPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class VelocityCommand implements SimpleCommand {

    private final VelocityHuskChat plugin;
    private final CommandBase command;

    public VelocityCommand(@NotNull CommandBase command, @NotNull VelocityHuskChat plugin) {
        this.command = command;
        this.plugin = plugin;

        // Register command
        plugin.getProxyServer().getCommandManager().register(
                command.getName(),
                this,
                command.getAliases().toArray(new String[0])
        );
    }

    @Override
    public void execute(@NotNull Invocation invocation) {
        if (invocation.source() instanceof Player player) {
            command.onExecute(VelocityPlayer.adapt(player), invocation.arguments());
        } else {
            command.onExecute(ConsolePlayer.create(plugin), invocation.arguments());
        }
    }

    @Override
    public List<String> suggest(@NotNull Invocation invocation) {
        if (invocation.source() instanceof Player player) {
            return command.onTabComplete(VelocityPlayer.adapt(player), invocation.arguments());
        }
        return List.of();
    }

    @Override
    public boolean hasPermission(@NotNull Invocation invocation) {
        return invocation.source().hasPermission(command.getPermission());
    }

    public enum Type {
        HUSKCHAT((plugin) -> Optional.of(new VelocityCommand(new HuskChatCommand(plugin), plugin))),
        CHANNEL((plugin) -> Optional.of(new VelocityCommand(new ChannelCommand(plugin), plugin))),
        MESSAGE((plugin) -> plugin.getSettings().isDoMessageCommand()
                ? Optional.of(new VelocityCommand(new MessageCommand(plugin), plugin)) : Optional.empty()),
        REPLY((plugin) -> plugin.getSettings().isDoMessageCommand()
                ? Optional.of(new VelocityCommand(new ReplyCommand(plugin), plugin)) : Optional.empty()),
        OPT_OUT_MESSAGE((plugin) -> plugin.getSettings().isDoMessageCommand()
                ? Optional.of(new VelocityCommand(new OptOutMessageCommand(plugin), plugin)) : Optional.empty()),
        BROADCAST((plugin) -> plugin.getSettings().isDoBroadcastCommand()
                ? Optional.of(new VelocityCommand(new BroadcastCommand(plugin), plugin)) : Optional.empty()),
        SOCIAL_SPY((plugin) -> plugin.getSettings().doSocialSpyCommand()
                ? Optional.of(new VelocityCommand(new SocialSpyCommand(plugin), plugin)) : Optional.empty()),
        LOCAL_SPY((plugin) -> plugin.getSettings().doLocalSpyCommand()
                ? Optional.of(new VelocityCommand(new LocalSpyCommand(plugin), plugin)) : Optional.empty());

        private final Function<VelocityHuskChat, Optional<VelocityCommand>> commandSupplier;

        Type(@NotNull Function<VelocityHuskChat, Optional<VelocityCommand>> commandSupplier) {
            this.commandSupplier = commandSupplier;
        }

        @NotNull
        private Optional<VelocityCommand> create(@NotNull VelocityHuskChat plugin) {
            return commandSupplier.apply(plugin);
        }

        public static List<VelocityCommand> getCommands(@NotNull VelocityHuskChat plugin) {
            return Arrays.stream(values())
                    .map(type -> type.create(plugin))
                    .filter(Optional::isPresent).map(Optional::get)
                    .toList();
        }

    }
    
}