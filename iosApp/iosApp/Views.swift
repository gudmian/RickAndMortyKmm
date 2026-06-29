import UIKit

/// Renders an emoji string into a UIImage (used for tab bar icons).
func emojiImage(_ emoji: String, size: CGFloat = 22) -> UIImage {
    let attributes: [NSAttributedString.Key: Any] = [.font: UIFont.systemFont(ofSize: size)]
    let textSize = (emoji as NSString).size(withAttributes: attributes)
    let renderer = UIGraphicsImageRenderer(size: textSize)
    return renderer.image { _ in
        (emoji as NSString).draw(at: .zero, withAttributes: attributes)
    }.withRenderingMode(.alwaysOriginal)
}

// MARK: - Loading / Error

final class LoadingView: UIView {
    private let activity = UIActivityIndicatorView(style: .large)
    init() {
        super.init(frame: .zero)
        addSubview(activity)
        activity.translatesAutoresizingMaskIntoConstraints = false
        NSLayoutConstraint.activate([
            activity.centerXAnchor.constraint(equalTo: centerXAnchor),
            activity.centerYAnchor.constraint(equalTo: centerYAnchor),
        ])
    }
    required init?(coder: NSCoder) { fatalError() }
    override var isHidden: Bool {
        didSet { isHidden ? activity.stopAnimating() : activity.startAnimating() }
    }
}

final class ErrorView: UIView {
    var onRetry: (() -> Void)?
    private let messageLabel = UILabel()
    private let retryButton = UIButton(type: .system)

    init() {
        super.init(frame: .zero)
        messageLabel.numberOfLines = 0
        messageLabel.textAlignment = .center
        messageLabel.font = .preferredFont(forTextStyle: .body)
        retryButton.setTitle("Retry", for: .normal)
        retryButton.addTarget(self, action: #selector(retryTapped), for: .touchUpInside)

        let stack = UIStackView(arrangedSubviews: [messageLabel, retryButton])
        stack.axis = .vertical
        stack.spacing = 12
        stack.alignment = .center
        addSubview(stack)
        stack.translatesAutoresizingMaskIntoConstraints = false
        NSLayoutConstraint.activate([
            stack.centerXAnchor.constraint(equalTo: centerXAnchor),
            stack.centerYAnchor.constraint(equalTo: centerYAnchor),
            stack.leadingAnchor.constraint(greaterThanOrEqualTo: leadingAnchor, constant: 24),
            stack.trailingAnchor.constraint(lessThanOrEqualTo: trailingAnchor, constant: -24),
        ])
    }
    required init?(coder: NSCoder) { fatalError() }

    var message: String? {
        get { messageLabel.text }
        set { messageLabel.text = newValue }
    }

    @objc private func retryTapped() { onRetry?() }
}

// MARK: - Detail info row

final class InfoRowView: UIView {
    private let titleLabel = UILabel()
    private let valueLabel = UILabel()

    init() {
        super.init(frame: .zero)
        titleLabel.font = .preferredFont(forTextStyle: .caption1)
        titleLabel.textColor = .secondaryLabel
        valueLabel.font = .preferredFont(forTextStyle: .body)
        valueLabel.numberOfLines = 0

        let stack = UIStackView(arrangedSubviews: [titleLabel, valueLabel])
        stack.axis = .vertical
        stack.spacing = 2
        addSubview(stack)
        stack.translatesAutoresizingMaskIntoConstraints = false
        NSLayoutConstraint.activate([
            stack.topAnchor.constraint(equalTo: topAnchor),
            stack.bottomAnchor.constraint(equalTo: bottomAnchor),
            stack.leadingAnchor.constraint(equalTo: leadingAnchor),
            stack.trailingAnchor.constraint(equalTo: trailingAnchor),
        ])
    }
    required init?(coder: NSCoder) { fatalError() }

    func configure(title: String, value: String) {
        titleLabel.text = title.uppercased()
        valueLabel.text = value
    }
}

// MARK: - Feed card cell

final class CardCell: UICollectionViewCell {
    static let reuseIdentifier = "CardCell"

    private let imageView = UIImageView()
    private let titleLabel = UILabel()
    private let subtitleLabel = UILabel()
    private let detailLabel = UILabel()
    private var imageTask: URLSessionDataTask?

    override init(frame: CGRect) {
        super.init(frame: frame)
        contentView.backgroundColor = .secondarySystemBackground
        contentView.layer.cornerRadius = 12

        imageView.contentMode = .scaleAspectFill
        imageView.clipsToBounds = true
        imageView.layer.cornerRadius = 8
        imageView.translatesAutoresizingMaskIntoConstraints = false
        imageView.widthAnchor.constraint(equalToConstant: 56).isActive = true
        imageView.heightAnchor.constraint(equalToConstant: 56).isActive = true

        titleLabel.font = .preferredFont(forTextStyle: .headline)
        subtitleLabel.font = .preferredFont(forTextStyle: .subheadline)
        subtitleLabel.textColor = .secondaryLabel
        detailLabel.font = .preferredFont(forTextStyle: .caption1)
        detailLabel.textColor = .secondaryLabel

        let textStack = UIStackView(arrangedSubviews: [titleLabel, subtitleLabel, detailLabel])
        textStack.axis = .vertical
        textStack.spacing = 2

        let row = UIStackView(arrangedSubviews: [imageView, textStack])
        row.axis = .horizontal
        row.spacing = 12
        row.alignment = .center
        contentView.addSubview(row)
        row.translatesAutoresizingMaskIntoConstraints = false
        NSLayoutConstraint.activate([
            row.topAnchor.constraint(equalTo: contentView.topAnchor, constant: 12),
            row.bottomAnchor.constraint(equalTo: contentView.bottomAnchor, constant: -12),
            row.leadingAnchor.constraint(equalTo: contentView.leadingAnchor, constant: 12),
            row.trailingAnchor.constraint(equalTo: contentView.trailingAnchor, constant: -12),
        ])
    }
    required init?(coder: NSCoder) { fatalError() }

    func configure(title: String, subtitle: String?, detail: String?, imageURL: String?) {
        titleLabel.text = title
        subtitleLabel.text = subtitle
        subtitleLabel.isHidden = (subtitle == nil)
        detailLabel.text = detail
        detailLabel.isHidden = (detail == nil)

        imageTask?.cancel()
        imageView.image = nil
        if let imageURL, let url = URL(string: imageURL) {
            imageView.isHidden = false
            imageTask = ImageLoader.shared.load(url: url, into: imageView)
        } else {
            imageView.isHidden = true
        }
    }

    override func prepareForReuse() {
        super.prepareForReuse()
        imageTask?.cancel()
        imageView.image = nil
    }
}
