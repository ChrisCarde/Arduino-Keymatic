/**
 *  Arduino Gateway KeyMatic
 *
 *  Copyright 2015 Chris Carde
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
metadata {
	definition (name: "Arduino Gateway KeyMatic", namespace: "ccarde", author: "Chris Carde") {
		capability "Door Control"
		capability "Lock"
	}

	simulator {
		// TODO: define status and reply messages here
	}

	tiles(scale: 2) {
		multiAttributeTile(name:"toggle", type: "generic", width: 6, height: 4){
			tileAttribute ("device.lock", key: "PRIMARY_CONTROL") {
				attributeState "locked", label:'locked', action:"lock.unlock", icon:"st.locks.lock.locked", backgroundColor:"#79b821", nextState:"unlocking"
				attributeState "unlocked", label:'unlocked', action:"lock.lock", icon:"st.locks.lock.unlocked", backgroundColor:"#ffffff", nextState:"locking"
				attributeState "unknown", label:"unknown", action:"lock.lock", icon:"st.locks.lock.unknown", backgroundColor:"#ffffff", nextState:"locking"
				attributeState "locking", label:'locking', icon:"st.locks.lock.locked", backgroundColor:"#79b821"
				attributeState "unlocking", label:'unlocking', icon:"st.locks.lock.unlocked", backgroundColor:"#ffffff"
			}
		}
		standardTile("lock", "device.lock", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
			state "default", label:'lock', action:"lock.lock", icon:"st.locks.lock.locked", nextState:"locking"
		}
		standardTile("unlock", "device.lock", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
			state "default", label:'unlock', action:"lock.unlock", icon:"st.locks.lock.unlocked", nextState:"unlocking"
		}
        standardTile("open", "device.lock", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
        	state "default", label:'open', action:"open", icon:"st.contact.contact.open", nextState:"opening"
        }
		standardTile("refresh", "device.lock", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
			state "default", label:'', action:"refresh.refresh", icon:"st.secondary.refresh"
		}

		main "toggle"
		details(["toggle", "lock", "unlock", "open", "refresh"])
	}
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
	// TODO: handle 'door' attribute
	// TODO: handle 'lock' attribute

}

// handle commands
def open() {
	log.debug "Executing 'open'"
    def result = new physicalgraph.device.HubAction(
        method: "GET",
        path: "/arduino/door/open",
        headers: [
            HOST: "10.9.5.40:80"
        ]
    )
    sendEvent(name: "lock", value: "unlocked")
    result
}

def close() {
	log.warn "KeyMatic 'close' event not implemented!"
	// TODO: handle 'close' command
}

def lock() {
	log.debug "Executing 'lock'"
    def result = new physicalgraph.device.HubAction(
        method: "GET",
        path: "/arduino/door/lock",
        headers: [
            HOST: "10.9.5.40:80"
        ]
    )
    sendEvent(name: "lock", value: "locked")
    result
}

def unlock() {
	log.debug "Executing 'unlock'"
    def result = new physicalgraph.device.HubAction(
        method: "GET",
        path: "/arduino/door/unlock",
        headers: [
            HOST: "10.9.5.40:80"
        ]
    )
    sendEvent(name: "lock", value: "unlocked")
    result
}


