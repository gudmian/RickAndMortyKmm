import UIKit
import SharedKit

final class CharacterDetailViewController: UIViewController {

    private let viewModel: CharacterDetailViewModel
    private var job: Kotlinx_coroutines_coreJob?
    private let scrollView = UIScrollView()
    private let contentStack = UIStackView()
    private let imageView = UIImageView()
    private let loadingView = LoadingView()
    private let errorView = ErrorView()
    private var rows: [InfoRowView] = []

    init(id: Int32) {
        viewModel = FeatureFactoryKt.characterDetailViewModel(id: id)
        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder: NSCoder) { fatalError("init(coder:) has not been implemented") }

    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .systemBackground
        navigationItem.largeTitleDisplayMode = .never

        addFullscreen(loadingView)
        addFullscreen(errorView)
        errorView.onRetry = { [weak self] in self?.viewModel.load() }

        scrollView.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(scrollView)
        NSLayoutConstraint.activate([
            scrollView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor),
            scrollView.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor),
            scrollView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            scrollView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
        ])

        contentStack.axis = .vertical
        contentStack.spacing = 6
        contentStack.translatesAutoresizingMaskIntoConstraints = false
        scrollView.addSubview(contentStack)
        NSLayoutConstraint.activate([
            contentStack.topAnchor.constraint(equalTo: scrollView.contentLayoutGuide.topAnchor, constant: 16),
            contentStack.bottomAnchor.constraint(equalTo: scrollView.contentLayoutGuide.bottomAnchor, constant: -16),
            contentStack.leadingAnchor.constraint(equalTo: scrollView.contentLayoutGuide.leadingAnchor, constant: 16),
            contentStack.trailingAnchor.constraint(equalTo: scrollView.contentLayoutGuide.trailingAnchor, constant: -16),
            contentStack.widthAnchor.constraint(equalTo: scrollView.frameLayoutGuide.widthAnchor, constant: -32),
        ])

        imageView.contentMode = .scaleAspectFill
        imageView.clipsToBounds = true
        imageView.layer.cornerRadius = 16
        imageView.heightAnchor.constraint(equalToConstant: 220).isActive = true
        contentStack.addArrangedSubview(imageView)

        for _ in 0..<7 {
            let row = InfoRowView()
            rows.append(row)
            contentStack.addArrangedSubview(row)
        }

        job = viewModel.observeState { [weak self] state in
            DispatchQueue.main.async { self?.apply(state) }
        }
    }

    deinit { job?.cancel(cause: nil); viewModel.destroy() }

    private func apply(_ state: CharacterDetailViewModel.State) {
        loadingView.isHidden = !state.isLoading
        let showError = state.error != nil
        errorView.isHidden = !showError
        if showError { errorView.message = state.error }
        guard let data = state.data else { return }

        title = data.name
        if let url = URL(string: data.image) { ImageLoader.shared.load(url: url, into: imageView) }
        rows[0].configure(title: "Status", value: data.status)
        rows[1].configure(title: "Species", value: data.species)
        rows[2].configure(title: "Type", value: data.type.isEmpty ? "—" : data.type)
        rows[3].configure(title: "Gender", value: data.gender)
        rows[4].configure(title: "Origin", value: data.originName.isEmpty ? "—" : data.originName)
        rows[5].configure(title: "Last known location", value: data.locationName.isEmpty ? "—" : data.locationName)
        rows[6].configure(title: "Episodes", value: "\(data.episodeIds.count)")
    }
}
