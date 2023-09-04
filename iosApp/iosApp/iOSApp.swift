import SwiftUI
import shared

@main
struct iOSApp: App {

    init() {
        AppModuleKt.doInitKoin()
        Main_iosKt.doInitLogger()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
