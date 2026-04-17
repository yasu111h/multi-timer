package com.multitimer.app.ui.stopwatch;

import com.multitimer.app.data.repository.StopwatchRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class StopwatchViewModel_Factory implements Factory<StopwatchViewModel> {
  private final Provider<StopwatchRepository> repositoryProvider;

  public StopwatchViewModel_Factory(Provider<StopwatchRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public StopwatchViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static StopwatchViewModel_Factory create(
      Provider<StopwatchRepository> repositoryProvider) {
    return new StopwatchViewModel_Factory(repositoryProvider);
  }

  public static StopwatchViewModel newInstance(StopwatchRepository repository) {
    return new StopwatchViewModel(repository);
  }
}
