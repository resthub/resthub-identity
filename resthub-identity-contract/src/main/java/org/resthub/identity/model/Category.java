package org.resthub.identity.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Category of permissions. Used to order permissions.
 *
 */
@Entity
@Table(name = "idm_categories")
public class Category {
	
	private Long id;
	private String name;
	private List<Permission> permissions;
	private List<Category> categories;
	private Category parent;
	
	/**
	 * Default constructor
	 */
	public Category(){
		
	}
	
	/**
	 * 
	 * @param name
	 * 			name of the Category to create
	 */
	public Category(String name){
		this.name = name;
	}
	
	/**
	 * Retrieve the id of the Category
	 * @return id of the Category
	 */
	@Id
    @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Set the id of the Category
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Retrieve the name of the Category
	 * @return name of the Category
	 */
	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}
	
	/**
	 * Set the name of the Category
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Retrieve the permissions of the category
	 * @return the permissions of the category
	 */
	@OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
	public List<Permission> getPermissions() {
		if (this.permissions == null){
			this.permissions = new ArrayList<Permission>();
		}
		return permissions;
	}
	
	/**
	 * Set the permissions of the category
	 * @param permissions
	 */
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	/**
	 * Retrieve the sub-categories of the category
	 * @return
	 */
	@OneToMany(mappedBy = "parent")
	public List<Category> getCategories() {
		if (this.categories == null){
			this.categories = new ArrayList<Category>();
		}
		return categories;
	}

	/**
	 * Set the sub-categories of the category
	 * @param categories
	 */
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	/**
	 * Retrieve the parent category
	 * @return
	 */
	@ManyToOne
	@JoinColumn(name="category_id")
	@JsonIgnore
	public Category getParent() {
		return parent;
	}

	/**
	 * Set the parent category
	 * @param parent
	 */
	public void setParent(Category parent) {
		this.parent = parent;
	}
	
	
	
}
