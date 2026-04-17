package com.multitimer.app.data.repository;

import com.multitimer.app.data.db.dao.TimerDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class TimerRepository_Factory implements Factory<TimerRepository> {
  private final Provider<TimerDao> timerDaoProvider;

  public TimerRepository_Factory(Provider<TimerDao> timerDaoProvider) {
    this.timerDaoProvider = timerDaoProvider;
  }

  @Override
  public TimerRepository get() {
    return newInstance(timerDaoProvider.get());
  }

  public static TimerRepository_Factory create(Provider<TimerDao> timerDaoProvider) {
    return new TimerRepository_Factory(timerDaoProvider);
  }

  public static TimerRepository newInstance(TimerDao timerDao) {
    return new TimerRepository(timerDao);
  }
}
