package io.github.droidkaigi.feeder.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.feeder.repository.ContributorRepository
import io.github.droidkaigi.feeder.repository.DeviceRepository
import io.github.droidkaigi.feeder.repository.FeedRepository
import io.github.droidkaigi.feeder.repository.StaffRepository

@InstallIn(SingletonComponent::class)
@Module
class DataModule {
    @Provides
    internal fun provideFeedApi(daggerApi: DaggerKtorFeedApi): FeedApi {
        return daggerApi
    }

    @Provides
    internal fun provideStaffApi(daggerApi: DaggerKtorStaffApi): StaffApi {
        return daggerApi
    }

    @Provides
    internal fun provideDeviceApi(daggerApi: DaggerKtorDeviceApi): DeviceApi {
        return daggerApi
    }

    @Provides
    internal fun provideContributorApi(daggerApi: DaggerKtorContributorApi): ContributorApi {
        return daggerApi
    }

    @Provides
    internal fun provideUserStore(daggerUserStore: DataStoreUserDataStore): UserDataStore {
        return daggerUserStore
    }

    @Provides
    internal fun provideFeedRepository(daggerRepository: DaggerFeedRepositoryImpl): FeedRepository {
        return daggerRepository
    }

    @Provides
    internal fun provideStaffRepository(
        daggerRepository: DaggerStaffRepositoryImpl
    ): StaffRepository {
        return daggerRepository
    }

    @Provides
    internal fun provideDeviceRepository(
        daggerRepository: DaggerDeviceRepositoryImpl
    ): DeviceRepository {
        return daggerRepository
    }

    @Provides
    internal fun provideContributorRepository(
        daggerRepository: DaggerContributorRepositoryImpl,
    ): ContributorRepository {
        return daggerRepository
    }
}
