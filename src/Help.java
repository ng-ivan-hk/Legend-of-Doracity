import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

@SuppressWarnings("serial")
public class Help extends JFrame {

	private ContentPane contentPane = null;

	public Help() {
		super(Lang.menu_help);

		try {
			setIconImage(new ImageIcon(Play.class.getResource("/resources/xander.png")).getImage());
		} catch (NullPointerException e) {
		}
		add(BorderLayout.WEST, new TreePanel());
		add(BorderLayout.CENTER, contentPane = new ContentPane());
		pack();
		setMinimumSize(getBounds().getSize());
		setSize(new Dimension(800,getWidth()));
		Play.locateCenter(this);
		setVisible(true);
	}

	private class TreePanel extends JScrollPane {

		private JTree tree = null;

		public TreePanel() {
			/* Create nodes */
			DefaultMutableTreeNode top = new DefaultMutableTreeNode(Lang.menu_help);
			createNodes(top);
			/* Create a tree and put nodes into it */
			tree = new JTree(top);
			tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			tree.addTreeSelectionListener(new TreeSelectionListener() {
				@Override
				public void valueChanged(TreeSelectionEvent e) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
							.getLastSelectedPathComponent();

					if (node == null)// Nothing is selected
						return;

					Object nodeInfo = node.getUserObject();
					if (node.isLeaf()) {
						HelpPage page = (HelpPage) nodeInfo;
						contentPane.setContent(page.getFilename());
					}
				}
			});
			/* Set Scroll Panel */
			setViewportView(tree);
			setPreferredSize(new Dimension(200, getHeight()));
		}

		private void createNodes(DefaultMutableTreeNode top) {
			DefaultMutableTreeNode category = null;

			top.add(new DefaultMutableTreeNode(new HelpPage(Lang.help_debug, "debug")));

			/* Game Rules */
			category = new DefaultMutableTreeNode(Lang.help_gameRules);
			top.add(category);

			category.add(new DefaultMutableTreeNode(new HelpPage(Lang.help_beforeGame,
					"before_game")));
			category.add(new DefaultMutableTreeNode(new HelpPage(Lang.help_gameFlow, "game_flow")));
			category.add(new DefaultMutableTreeNode(new HelpPage(Lang.help_vocab, "vocab")));
			category.add(new DefaultMutableTreeNode(new HelpPage(Lang.help_cards, "cards")));
			category.add(new DefaultMutableTreeNode(new HelpPage(Lang.help_QA, "QA")));

			/* App Help */
			category = new DefaultMutableTreeNode(Lang.help_appHelp);
			top.add(category);

			category.add(new DefaultMutableTreeNode(new HelpPage(Lang.help_preload, "preload")));
			category.add(new DefaultMutableTreeNode(new HelpPage(Lang.help_icons, "icons")));
			category.add(new DefaultMutableTreeNode(new HelpPage(Lang.help_appFAQ, "appFAQ")));
		}

	}

	private class ContentPane extends JScrollPane {

		private JEditorPane editorPane = null;

		public ContentPane() {
			/* Set Editor Pane */
			editorPane = new JEditorPane();
			editorPane.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));
			editorPane.setEditable(false);

			setContent("debug");

			// Put the editor pane into this (Scroll Panel)
			setViewportView(editorPane);
			setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			setPreferredSize(new Dimension(500, 600));
			setMinimumSize(new Dimension(50, 50));
		}

		public void setContent(String filename) {
			java.net.URL helpURL = Play.class.getResource("/resources/help/" + filename + ".html");
			if (helpURL != null) {
				try {
					// Put the HTML file into the Editor Pane
					editorPane.setPage(helpURL);
				} catch (IOException e) { // Bad URL
					JOptionPane.showMessageDialog(null, Lang.fileNotFound + helpURL, Lang.error,
							JOptionPane.ERROR_MESSAGE);
					return;
				}

			} else { // File not found
				JOptionPane.showMessageDialog(null, Lang.fileNotFound + helpURL, Lang.error,
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

	}

	private class HelpPage {
		private String title;
		private String filename;

		public HelpPage(String title, String filename) {
			this.title = title;
			this.filename = filename;
		}

		public String toString() {
			return title;
		}

		public String getFilename() {
			return filename;
		}
	}

}
