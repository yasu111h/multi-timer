package com.multitimer.app.data.di;

import com.multitimer.app.data.db.AppDatabase;
import com.multitimer.app.data.db.dao.TimerDao;
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
public final class DatabaseModule_ProvideTimerDaoFactory implements Factory<TimerDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideTimerDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public TimerDao get() {
    return provideTimerDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideTimerDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideTimerDaoFactory(dbProvider);
  }

  public static TimerDao provideTimerDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTimerDao(db));
  }
}
