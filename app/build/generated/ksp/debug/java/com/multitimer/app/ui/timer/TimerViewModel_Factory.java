package com.multitimer.app.ui.timer;

import android.content.Context;
import com.multitimer.app.data.repository.TimerRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class TimerViewModel_Factory implements Factory<TimerViewModel> {
  private final Provider<TimerRepository> repositoryProvider;

  private final Provider<Context> contextProvider;

  public TimerViewModel_Factory(Provider<TimerRepository> repositoryProvider,
      Provider<Context> contextProvider) {
    this.repositoryProvider = repositoryProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public TimerViewModel get() {
    return newInstance(repositoryProvider.get(), contextProvider.get());
  }

  public static TimerViewModel_Factory create(Provider<TimerRepository> repositoryProvider,
      Provider<Context> contextProvider) {
    return new TimerViewModel_Factory(repositoryProvider, contextProvider);
  }

  public static TimerViewModel newInstance(TimerRepository repository, Context context) {
    return new TimerViewModel(repository, context);
  }
}
