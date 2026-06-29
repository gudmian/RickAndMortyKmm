import UIKit
import SharedKit

final class LocationDetailViewController: UIViewController {

    private let viewModel: LocationDetailViewModel
    private var job: Kotlinx_coroutines_coreJob?
    private let scrollView = UIScrollView()
    private let contentStack = UIStackView()
    private let loadingView = LoadingView()
    private let errorView = ErrorView()
    private var rows: [InfoRowView] = []

    init(id: Int32) {
        viewModel = FeatureFactoryKt.locationDetailViewModel(id: id)
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

        for _ in 0..<3 {
            let row = InfoRowView()
            rows.append(row)
            contentStack.addArrangedSubview(row)
        }

        job = viewModel.observeState { [weak self] state in
            DispatchQueue.main.async { self?.apply(state) }
        }
    }

    deinit { job?.cancel(cause: nil); viewModel.destroy() }

    private func apply(_ state: LocationDetailViewModel.State) {
        loadingView.isHidden = !state.isLoading
        let showError = state.error != nil
        errorView.isHidden = !showError
        if showError { errorView.message = state.error }
        guard let data = state.data else { return }

        title = data.name
        rows[0].configure(title: "Type", value: data.type.isEmpty ? "—" : data.type)
        rows[1].configure(title: "Dimension", value: data.dimension.isEmpty ? "—" : data.dimension)
        rows[2].configure(title: "Residents", value: "\(data.residentIds.count)")
    }
}
