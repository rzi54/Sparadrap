package fr.luzi.utilities.graphics;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class CustomJtree extends DefaultTreeCellRenderer {
    
	private static final long serialVersionUID = 6971750843799092760L;
	
	public ImageIcon nodeIcon;
	public ImageIcon leafIcon;
	public ImageIcon rootIcon;


	public CustomJtree(ImageIcon rootIcon_, ImageIcon nodeIcon_, ImageIcon leafIcon_) {

        if (nodeIcon_ != null && leafIcon_ != null && rootIcon_ != null) {
            nodeIcon = nodeIcon_;
            leafIcon = leafIcon_;
            rootIcon = rootIcon_;
        }
    }

	@Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
                                                  boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        if (node.isRoot()) {
            setIcon(rootIcon);
        } else if (leaf) {
            setIcon(leafIcon);
        } else {
            setIcon(nodeIcon);
        }
        
        if (value instanceof DefaultMutableTreeNode) {
            Object userObject = node.getUserObject();
            
            if (userObject != null) {

                String text = userObject.toString();
            	if (node.isRoot()) {
	                setText("<html><b>" + text + "</b></html>");
            	} else if (leaf) {
                    
                } else {
                    setText("<html><b>" + text + "</b></html>");
                }
            }
        }
        
        return this;
    }
}