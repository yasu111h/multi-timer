package com.multitimer.app.data.repository;

import com.multitimer.app.data.db.dao.LapDao;
import com.multitimer.app.data.db.dao.StopwatchDao;
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
public final class StopwatchRepository_Factory implements Factory<StopwatchRepository> {
  private final Provider<StopwatchDao> stopwatchDaoProvider;

  private final Provider<LapDao> lapDaoProvider;

  public StopwatchRepository_Factory(Provider<StopwatchDao> stopwatchDaoProvider,
      Provider<LapDao> lapDaoProvider) {
    this.stopwatchDaoProvider = stopwatchDaoProvider;
    this.lapDaoProvider = lapDaoProvider;
  }

  @Override
  public StopwatchRepository get() {
    return newInstance(stopwatchDaoProvider.get(), lapDaoProvider.get());
  }

  public static StopwatchRepository_Factory create(Provider<StopwatchDao> stopwatchDaoProvider,
      Provider<LapDao> lapDaoProvider) {
    return new StopwatchRepository_Factory(stopwatchDaoProvider, lapDaoProvider);
  }

  public static StopwatchRepository newInstance(StopwatchDao stopwatchDao, LapDao lapDao) {
    return new StopwatchRepository(stopwatchDao, lapDao);
  }
}
