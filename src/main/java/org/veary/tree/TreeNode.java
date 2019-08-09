/*
 * MIT License
 *
 * Copyright (c) 2019 ColonelBlimp
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.veary.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 *
 * @author Marc L. Veary
 * @since 1.0
 * @param <T>
 */
public final class TreeNode<T> implements Iterable<TreeNode<T>> {

    private final T data;
    private final List<TreeNode<T>> children;
    private final List<TreeNode<T>> elements;
    private TreeNode<T> parent;

    /**
     * Constructor.
     *
     * @param data object of type {@code T}
     */
    public TreeNode(T data) {
        this.data = Objects.requireNonNull(data, "Parameter 'data' cannot be null.");
        this.children = new ArrayList<>();
        this.elements = new ArrayList<>();
        this.elements.add(this);
    }

    /**
     * Add a child node to this node.
     *
     * @param child object of type {@code T}
     * @return {@code TreeNode<T>} object of the added child node. Non-{@code null}.
     */
    public TreeNode<T> addChild(T child) {
        Objects.requireNonNull(child, "Parameter 'child' cannot be null.");
        TreeNode<T> childNode = new TreeNode<>(child);
        childNode.parent = this;
        this.children.add(0, childNode);
        return childNode;
    }

    /**
     * Returns the root node for this node.
     *
     * @return {@code TreeNode<T>} object. Non-{@code null}.
     */
    public TreeNode<T> getRoot() {
        if (this.parent == null) {
            return this;
        }
        return this.parent.getRoot();
    }

    /**
     * Returns a {@code List<TreeNode<T>>} of this node's childen.
     *
     * @return non-{@code null}
     */
    public List<TreeNode<T>> getChildren() {
        return this.children;
    }

    /**
     * Returns the parent node of this node.
     *
     * @return {@code TreeNode<T>} object. Non-{@code null}.
     * @throws NoSuchElementException if this is the root node (i.e. has no parent)
     */
    public TreeNode<T> getParent() {
        if (this.parent != null) {
            return this.parent;
        }
        throw new NoSuchElementException("This is the root node.");
    }

    /**
     * Returns the data associated with this node.
     *
     * @return an object of type {@code T}. Non-{@code null}.
     */
    public T getData() {
        return this.data;
    }

    /**
     * Checks if this node is the root node (i.e. has no parent).
     *
     * @return {@code true} if it is the root node, otherwise {@code false}
     */
    public boolean isRoot() {
        return this.parent == null;
    }

    /**
     * Returns {@code true} if this is the last node (i.e. has no children), otherwise
     * {@code false}.
     *
     * @return boolean
     */
    public boolean isLeaf() {
        return this.children.isEmpty();
    }

    public int getLevel() {
        if (this.isRoot()) {
            return 0;
        } else {
            return this.parent.getLevel() + 1;
        }
    }

    @Override
    public Iterator<TreeNode<T>> iterator() {
        return new TreeNodeIterator<>(this);
    }
}
