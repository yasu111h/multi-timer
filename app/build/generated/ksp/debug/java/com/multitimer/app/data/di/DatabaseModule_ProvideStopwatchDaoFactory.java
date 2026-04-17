package com.multitimer.app.data.di;

import com.multitimer.app.data.db.AppDatabase;
import com.multitimer.app.data.db.dao.StopwatchDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class DatabaseModule_ProvideStopwatchDaoFactory implements Factory<StopwatchDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideStopwatchDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public StopwatchDao get() {
    return provideStopwatchDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideStopwatchDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideStopwatchDaoFactory(dbProvider);
  }

  public static StopwatchDao provideStopwatchDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideStopwatchDao(db));
  }
}
