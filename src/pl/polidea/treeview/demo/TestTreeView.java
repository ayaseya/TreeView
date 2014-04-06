package pl.polidea.treeview.demo;

import java.util.HashSet;
import java.util.Set;

import pl.polidea.treeview.InMemoryTreeStateManager;
import pl.polidea.treeview.R;
import pl.polidea.treeview.TreeBuilder;
import pl.polidea.treeview.TreeStateManager;
import pl.polidea.treeview.TreeViewList;
import android.app.Activity;
import android.os.Bundle;

public class TestTreeView extends Activity {
	// 全て展開して表示する
	// manager.expandEverythingBelow(null);
	// 全て閉じる
	// manager.collapseChildren(null);

	private final Set<Long> selected = new HashSet<Long>();

	private static final String TAG = TreeViewListDemo.class.getSimpleName();
	private TreeViewList treeView;
	
	// 0が親
	// 1が子
	// 2が孫
	private static final int[] DEMO_NODES = new int[] { 0,
														0, 1, 1, 2,
														0, 1, 1, 1 };
	private static final int LEVEL_NUMBER = 3;
	private TreeStateManager<Long> manager = null;
	private TreeViewAdapter simpleAdapter;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {
			manager = new InMemoryTreeStateManager<Long>();
			final TreeBuilder<Long> treeBuilder = new TreeBuilder<Long>(manager);
			for (int i = 0; i < DEMO_NODES.length; i++) {
				treeBuilder.sequentiallyAddNextNode((long) i, DEMO_NODES[i]);
			}
			//			Log.v(TAG, manager.toString());

		} else {
			manager = (TreeStateManager<Long>) savedInstanceState.getSerializable("treeManager");
			if (manager == null) {
				manager = new InMemoryTreeStateManager<Long>();
			}

		}
		setContentView(R.layout.main_demo);

		treeView = (TreeViewList) findViewById(R.id.mainTreeView);
		simpleAdapter = new TreeViewAdapter(this, selected, manager, LEVEL_NUMBER);
		treeView.setAdapter(simpleAdapter);
		manager.collapseChildren(null);

	}

	@Override
	protected void onSaveInstanceState(final Bundle outState) {
		outState.putSerializable("treeManager", manager);
		super.onSaveInstanceState(outState);
	}

}
