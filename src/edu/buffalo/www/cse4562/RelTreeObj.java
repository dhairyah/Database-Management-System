package edu.buffalo.www.cse4562;
 
import java.util.ArrayList;
import java.util.List;
 
public class RelTreeObj<O> {
	public O data = null;
	private RelTreeObj<O> parent = null;
	private List<RelTreeObj<O>> child = new ArrayList<>();
 
	public RelTreeObj(O data) {
	this.data = data;
	}
 
	public RelTreeObj<O> addChild(RelTreeObj<O> child) {
	child.setParent(this);
	this.child.add(child);
	return child;
	}
 
	public void addChildren(List<RelTreeObj<O>> children) {
	children.forEach(each -> each.setParent(this));
	this.child.addAll(children);
	}
 
	public List<RelTreeObj<O>> getChildren() {
	return child;
	}
 
	public O getData() {
	return data;
	}
 
	public void setData(O data) {
	this.data = data;
	}
 
	private void setParent(RelTreeObj<O> parent) {
	this.parent = parent;
	}
 
	public RelTreeObj<O> getParent() {
	return parent;
	}
}