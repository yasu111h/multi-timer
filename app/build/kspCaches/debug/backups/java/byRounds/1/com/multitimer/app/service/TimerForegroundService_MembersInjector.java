package com.multitimer.app.service;

import com.multitimer.app.data.repository.TimerRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class TimerForegroundService_MembersInjector implements MembersInjector<TimerForegroundService> {
  private final Provider<TimerRepository> timerRepositoryProvider;

  public TimerForegroundService_MembersInjector(Provider<TimerRepository> timerRepositoryProvider) {
    this.timerRepositoryProvider = timerRepositoryProvider;
  }

  public static MembersInjector<TimerForegroundService> create(
      Provider<TimerRepository> timerRepositoryProvider) {
    return new TimerForegroundService_MembersInjector(timerRepositoryProvider);
  }

  @Override
  public void injectMembers(TimerForegroundService instance) {
    injectTimerRepository(instance, timerRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.multitimer.app.service.TimerForegroundService.timerRepository")
  public static void injectTimerRepository(TimerForegroundService instance,
      TimerRepository timerRepository) {
    instance.timerRepository = timerRepository;
  }
}
