package net.tnemc.plugincore.sponge.impl.scheduler;
/*
 * The New Plugin Core
 * Copyright (C) 2022 - 2024 Daniel "creatorfromhell" Vidmar
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import net.tnemc.plugincore.core.compatibility.scheduler.Chore;
import net.tnemc.plugincore.core.compatibility.scheduler.ChoreExecution;
import net.tnemc.plugincore.core.compatibility.scheduler.ChoreTime;
import net.tnemc.plugincore.core.compatibility.scheduler.SchedulerProvider;
import net.tnemc.plugincore.sponge.SpongePluginCore;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.TaskExecutorService;

import java.util.concurrent.TimeUnit;

/**
 * SpongeScheduler
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class SpongeScheduler extends SchedulerProvider<SpongeChore> {

  /**
   * Used to create a task, which will execute after the specified delay.
   *
   * @param task        The task to run.
   * @param delay       The delay, in ticks.
   * @param environment The execution environment for the task.
   */
  @Override
  public void createDelayedTask(final Runnable task, final ChoreTime delay, final ChoreExecution environment) {
    //divide by 20 because the delay will be coming in tick for.
    Sponge.asyncScheduler().executor(SpongePluginCore.instance().getContainer()).schedule(task, delay.asSeconds(), TimeUnit.SECONDS);
  }

  /**
   * Used to create a task, which repeats after a specified period.
   *
   * @param task        The task to run.
   * @param delay       The delay to run the task, in ticks.
   * @param period      The period to run the task.
   * @param environment The execution environment for the task.
   *
   * @return The associated {@link Chore} with this task.
   */
  @Override
  public SpongeChore createRepeatingTask(final Runnable task, final ChoreTime delay, final ChoreTime period, final ChoreExecution environment) {

    final TaskExecutorService service = Sponge.asyncScheduler().executor(SpongePluginCore.instance().getContainer());

    //divide by 20 because the delay will be coming in tick for.
    return new SpongeChore(service.scheduleAtFixedRate(task,
                                                       delay.asSeconds(),
                                                       period.asSeconds(),
                                                       TimeUnit.SECONDS).task(), environment);
  }
}
