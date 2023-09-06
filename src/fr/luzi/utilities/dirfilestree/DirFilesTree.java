package fr.luzi.utilities.dirfilestree;

/**
 * Classe dédiée à la gestion des arborescences (JTree).
 *
 * @language JAVA
 * @author Randy LUZI
 * @mail randy.luzi@gmail.com
 * @license MIT
*/

import java.io.File;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class DirFilesTree {

	/**
	 * Cette méthode génère une arborescence de fichiers en explorant dans une
	 * profondeur de deux. Elle peut-être grandement améliorée via l'emploi de la
	 * récursivité.
	 * 
	 * @param (String) dirName : le nom du fichier dans lequel explorer.
	 * @return (JTree) : Une arborescence.
	 */
	public static JTree generateJtree(String dirName) {

		String nameDirNameFisrtUpperCase = dirName.substring(0, 1).toUpperCase() + dirName.substring(1);

		DefaultMutableTreeNode root = new DefaultMutableTreeNode(nameDirNameFisrtUpperCase);
		DefaultMutableTreeNode node;
		DefaultMutableTreeNode leaf;

		File[] files = new File("ressources/" + dirName + "/").listFiles();

		for (File file : files) {

			String nameElement = file.getName();

			try {

				if (file.isFile()) {

				} else if (file.isDirectory()) {

					String nameElementFisrtUpperCase = nameElement.substring(0, 1).toUpperCase()
							+ nameElement.substring(1);
					node = new DefaultMutableTreeNode(nameElementFisrtUpperCase);

					File[] files_ = new File("ressources/" + dirName + "/" + nameElement + "/").listFiles();

					for (File file_ : files_) {

						String nameElement_ = file_.getName();

						try {

							if (file_.isFile()) {

								leaf = new DefaultMutableTreeNode(nameElement_);
								node.add(leaf);

							}

						} catch (Exception e) {
							e.printStackTrace();
						}

					}

					root.add(node);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		JTree jt = new JTree(root);
		return jt;

	}

	/**
	 * Obtenir le chemin d'un élément sélectionné dans un JTree.
	 * 
	 * @param Un chemin racine de l'arborescence.
	 * @return Un objet contenant les chemins trouvés.
	 */
	public static String getJTreePath(TreePath tp) {

		String value = "";
		Object elements[] = tp.getPath();

		for (int i = 0, n = elements.length; i < n; i++) {

			if (i < n - 1) {
				value += elements[i] + "\\";
			} else {

				value += elements[i];
			}
		}

		return value;
	}
}
