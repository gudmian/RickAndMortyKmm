import UIKit

final class MainTabBarController: UITabBarController {
    override func viewDidLoad() {
        super.viewDidLoad()
        viewControllers = [
            nav(CharacterFeedViewController(), title: "Characters", icon: "person.2", selectedIcon: "person.2.fill"),
            nav(LocationFeedViewController(), title: "Locations", icon: "globe", selectedIcon: "globe"),
            nav(EpisodeFeedViewController(), title: "Episodes", icon: "play.rectangle", selectedIcon: "play.rectangle.fill"),
        ]
        selectedIndex = 0
    }

    private func nav(_ root: UIViewController, title: String, icon: String, selectedIcon: String) -> UINavigationController {
        root.title = title
        root.tabBarItem = UITabBarItem(
            title: title,
            image: UIImage(systemName: icon),
            selectedImage: UIImage(systemName: selectedIcon)
        )
        let navigationController = UINavigationController(rootViewController: root)
        navigationController.navigationBar.prefersLargeTitles = true
        return navigationController
    }
}
