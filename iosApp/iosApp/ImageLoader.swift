import UIKit

/// Minimal remote image loader: URLSession + NSCache. No third-party dependencies.
final class ImageLoader {
    static let shared = ImageLoader()

    private let cache = NSCache<NSURL, UIImage>()
    private let session: URLSession

    private init() {
        let config = URLSessionConfiguration.ephemeral
        config.timeoutIntervalForRequest = 30
        config.urlCache = URLCache(memoryCapacity: 20 * 1024 * 1024, diskCapacity: 80 * 1024 * 1024)
        session = URLSession(configuration: config)
    }

    @discardableResult
    func load(url: URL, into imageView: UIImageView) -> URLSessionDataTask? {
        if let cached = cache.object(forKey: url as NSURL) {
            imageView.image = cached
            return nil
        }
        imageView.image = nil
        let task = session.dataTask(with: url) { [weak self, weak imageView] data, _, _ in
            guard let data, let image = UIImage(data: data) else { return }
            self?.cache.setObject(image, forKey: url as NSURL)
            DispatchQueue.main.async { imageView?.image = image }
        }
        task.resume()
        return task
    }
}
