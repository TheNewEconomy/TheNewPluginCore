package net.tnemc.plugincore.core.id.impl;

/*
 * The New Economy Minecraft Server Plugin
 * <p>
 * Created by creatorfromhell on 9/9/2020.
 * <p>
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by creatorfromhell on 06/30/2017.
 */

import net.tnemc.plugincore.core.id.UUIDAPI;

/**
 * Represents the AshCon API.
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class AshconAPI implements UUIDAPI {

  /**
   * @return The URL for this UUID API Service.
   */
  @Override
  public String url() {

    return "https://api.ashcon.app/mojang/v2/user/";
  }
}
