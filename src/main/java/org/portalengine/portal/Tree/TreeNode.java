package org.portalengine.portal.Tree;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "portal_tree_node")
public class TreeNode {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "tree_id" )
	private Tree tree; 
	
	@NotNull
	private String name;
	
	@OneToMany(
			mappedBy = "node",
			orphanRemoval = true)
	private List<TreeUser> users = new ArrayList<>();
	
	@OneToMany(
			mappedBy = "parent",
			orphanRemoval = true)
	private List<TreeNode> children = new ArrayList<>();
	
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "parent_id" )
	private TreeNode parent;
	
	@NotNull
	private Long lft;
	
	@NotNull
	private Long rgt;
	
	@OneToOne(mappedBy = "root")
    private Tree rootTree;
}