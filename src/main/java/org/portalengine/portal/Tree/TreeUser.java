package org.portalengine.portal.Tree;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.portalengine.portal.User.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "portal_tree_user")
public class TreeUser {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "node_id" )
	@JsonIgnore
	private TreeNode node;
	
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "user_id" )
	private User user;
	
	@NotNull
	private String role;
	
}
