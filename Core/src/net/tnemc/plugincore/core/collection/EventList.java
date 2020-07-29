/**
 * The New Economy Library Minecraft Server Plugin
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by creatorfromhell on 06/30/2017.
 */
package net.tnemc.plugincore.core.collection;

import net.tnemc.plugincore.core.TNPCore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by creatorfromhell on 11/2/2016.
 **/
public class EventList<E> extends ArrayList<E> {
  private ListListener<E> listener;
  private List<E> list = new ArrayList<>();
  private long lastRefresh = new Date().getTime();

  public EventList() {
    super();
  }

  public EventList(ListListener listener) {
    super();
    this.listener = listener;
  }

  public void update() {
    listener.update();
    listener.clearChanged();
    lastRefresh = new Date().getTime();
  }

  public Collection<E> getAll() {
    if(TNPCore.saveManager().getDataManager().getDb().supportUpdate() || TNPCore.saveManager().getDataManager().isCacheData()) {
      return list;
    }
    return listener.getAll();
  }

  public Collection<E> getAll(Object identifier) {
    if(TNPCore.saveManager().getDataManager().getDb().supportUpdate() || TNPCore.saveManager().getDataManager().isCacheData()) {
      return getAll();
    }
    return listener.getAll(identifier);
  }

  @Override
  public E get(int index) {
    return list.get(index);
  }

  @Override
  public int size() {
    return listener.size();
  }

  @Override
  public boolean isEmpty() {
    return listener.isEmpty();
  }

  @Override
  public boolean add(E item) {
    return add(item, false);
  }

  public boolean add(E item, boolean skip) {

    if(!TNPCore.saveManager().getDataManager().getDb().supportUpdate() || TNPCore.saveManager().getDataManager().isCacheData()) {
      if(TNPCore.saveManager().getDataManager().getDb().supportUpdate() && TNPCore.saveManager().getDataManager().isCacheData() && !contains(item) && !skip) {
        listener.add(item);
      }

      if(TNPCore.saveManager().getDataManager().isCacheData() && contains(item)) {
        listener.changed().add(item);
      }
      return list.add(item);
    }
    return listener.add(item);
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    return addAll(c, false);
  }

  public boolean addAll(Collection<? extends E> c, boolean skip) {
    for(E element : c) {
      add(element, skip);
    }
    return true;
  }

  @Override
  public E remove(int index) {
    E value = get(index);
    remove(value);
    return value;
  }

  @Override
  public boolean remove(Object item) {
    return remove(item, true);
  }

  public boolean remove(Object item, boolean database) {
    boolean removed = true;
    if(TNPCore.saveManager().getDataManager().getDb().supportUpdate()) {
      listener.preRemove(item);
    }

    if(!TNPCore.saveManager().getDataManager().getDb().supportUpdate() || TNPCore.saveManager().getDataManager().isCacheData()) {
      removed = list.remove(item);
    }

    if(TNPCore.saveManager().getDataManager().getDb().supportUpdate() && !TNPCore.saveManager().getDataManager().isCacheData() || database) {
      removed = listener.remove(item);
    }
    return removed;
  }

  public EventListIterator<E> getIterator() {
    return new EventListIterator<>(list.iterator(), listener);
  }

  public void setListener(ListListener<E> listener) {
    this.listener = listener;
  }
}