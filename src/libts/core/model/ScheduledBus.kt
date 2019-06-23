package libts.core.model

import com.soywiz.klock.DateTimeTz

/**
 * Represents a singular bus along a [Route], scheduled to pass at a [Stop]
 *
 * @property stop the [Stop] we are referencing
 * @property time the [DateTimeTz][time] at which the bus is scheduled to arrive
 */
class ScheduledBus(val stop: Stop, val time: DateTimeTz) {

    override fun toString(): String {
        return time.toString()
    }

}