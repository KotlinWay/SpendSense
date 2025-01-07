import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
       ComposeView()
    }
}

struct ComposeView : UIViewControllerRepresentable {
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {}
    
    func makeUIViewController(context: Context) -> some UIViewController {
        RootViewKt.rootViewController(root:  RootViewKt
            .getRootFactory()
            .create(context: DefaultComponentContext(
                                 lifecycle: ApplicationLifecycle()
                            )
            ) )
    }
}
