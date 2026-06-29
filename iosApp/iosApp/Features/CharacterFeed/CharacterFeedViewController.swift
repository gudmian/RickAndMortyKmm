import UIKit
import SharedKit

final class CharacterFeedViewController: UIViewController, UICollectionViewDataSource, UICollectionViewDelegate {

    private let viewModel = FeatureFactoryKt.characterFeedViewModel()
    private var state: CharacterFeedViewModel.State?
    private var job: Kotlinx_coroutines_coreJob?
    private var collectionView: UICollectionView!
    private let loadingView = LoadingView()
    private let errorView = ErrorView()

    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .systemBackground
        setupCollectionView()
        addFullscreen(loadingView)
        addFullscreen(errorView)
        errorView.onRetry = { [weak self] in self?.viewModel.retry() }

        job = viewModel.observeState { [weak self] state in
            DispatchQueue.main.async { self?.apply(state) }
        }
    }

    deinit { job?.cancel(cause: nil); viewModel.destroy() }

    private func apply(_ state: CharacterFeedViewModel.State) {
        self.state = state
        collectionView.reloadData()
        loadingView.isHidden = !(state.isLoading && state.items.isEmpty)
        let showError = state.error != nil && state.items.isEmpty
        errorView.isHidden = !showError
        if showError { errorView.message = state.error }
    }

    private func setupCollectionView() {
        let layout = UICollectionViewCompositionalLayout.list(
            using: UICollectionLayoutListConfiguration(appearance: .plain)
        )
        collectionView = UICollectionView(frame: .zero, collectionViewLayout: layout)
        collectionView.dataSource = self
        collectionView.delegate = self
        collectionView.register(CardCell.self, forCellWithReuseIdentifier: CardCell.reuseIdentifier)
        collectionView.backgroundColor = .systemGroupedBackground
        view.addSubview(collectionView)
        collectionView.translatesAutoresizingMaskIntoConstraints = false
        NSLayoutConstraint.activate([
            collectionView.topAnchor.constraint(equalTo: view.topAnchor),
            collectionView.bottomAnchor.constraint(equalTo: view.bottomAnchor),
            collectionView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            collectionView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
        ])
    }

    func collectionView(_ cv: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        state?.items.count ?? 0
    }

    func collectionView(_ cv: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = cv.dequeueReusableCell(withReuseIdentifier: CardCell.reuseIdentifier, for: indexPath) as! CardCell
        if let item = state?.items[indexPath.item] {
            cell.configure(
                title: item.name,
                subtitle: "\(item.status) · \(item.species)",
                detail: nil,
                imageURL: item.image
            )
        }
        return cell
    }

    func collectionView(_ cv: UICollectionView, willDisplay cell: UICollectionViewCell, forItemAt indexPath: IndexPath) {
        guard let s = state, s.items.count > 0 else { return }
        if indexPath.item >= s.items.count - 3, !s.isLoadingMore, !s.endReached, s.error == nil {
            viewModel.loadMore()
        }
    }

    func collectionView(_ cv: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        guard let id = state?.items[indexPath.item].id else { return }
        navigationController?.pushViewController(CharacterDetailViewController(id: id), animated: true)
    }
}
