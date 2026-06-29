import UIKit

final class MainTabBarController: UITabBarController {
    override func viewDidLoad() {
        super.viewDidLoad()
        viewControllers = [
            nav(CharacterFeedViewController(), title: "Characters", icon: "👥"),
            nav(LocationFeedViewController(), title: "Locations", icon: "📍"),
            nav(EpisodeFeedViewController(), title: "Episodes", icon: "🎬"),
        ]
        selectedIndex = 0
    }

    private func nav(_ root: UIViewController, title: String, icon: String) -> UINavigationController {
        root.title = title
        root.tabBarItem = UITabBarItem(title: title, image: emojiImage(icon), tag: 0)
        let navigationController = UINavigationController(rootViewController: root)
        navigationController.navigationBar.prefersLargeTitles = true
        return navigationController
    }
}
