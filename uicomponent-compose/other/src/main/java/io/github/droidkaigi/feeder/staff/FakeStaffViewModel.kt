package io.github.droidkaigi.feeder.staff

import io.github.droidkaigi.feeder.AppError
import io.github.droidkaigi.feeder.Staff
import io.github.droidkaigi.feeder.fakeStaffs
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

fun fakeStaffViewModel(errorFetchData: Boolean = false) = FakeStaffViewModel(errorFetchData)
class FakeStaffViewModel(errorFetchData: Boolean) : StaffViewModel {

    private val effectChannel = Channel<StaffViewModel.Effect>(Channel.UNLIMITED)
    override val effect: Flow<StaffViewModel.Effect> = effectChannel.receiveAsFlow()

    private val coroutineScope = CoroutineScope(
        object : CoroutineDispatcher() {
            // for preview
            override fun dispatch(context: CoroutineContext, block: Runnable) {
                block.run()
            }
        }
    )

    private val mutableStaffContents = MutableStateFlow(
        fakeStaffs()
    )

    private val errorStaffContents = flow<List<Staff>> {
        throw AppError.ApiException.ServerException(null)
    }.catch { error ->
        effectChannel.send(StaffViewModel.Effect.ErrorMessage(error as AppError))
    }.stateIn(coroutineScope, SharingStarted.Lazily, fakeStaffs())

    private val mStaffContents: StateFlow<List<Staff>> = if (errorFetchData) {
        errorStaffContents
    } else {
        mutableStaffContents
    }

    override val state: StateFlow<StaffViewModel.State> = mStaffContents.map {
        StaffViewModel.State(
            showProgress = false,
            staffContents = it
        )
    }.stateIn(coroutineScope, SharingStarted.Eagerly, StaffViewModel.State())

    override fun event(event: StaffViewModel.Event) {
    }
}
