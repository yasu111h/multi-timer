package com.multitimer.app.data.di;

import com.multitimer.app.data.db.AppDatabase;
import com.multitimer.app.data.db.dao.LapDao;
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
public final class DatabaseModule_ProvideLapDaoFactory implements Factory<LapDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideLapDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public LapDao get() {
    return provideLapDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideLapDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideLapDaoFactory(dbProvider);
  }

  public static LapDao provideLapDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideLapDao(db));
  }
}
