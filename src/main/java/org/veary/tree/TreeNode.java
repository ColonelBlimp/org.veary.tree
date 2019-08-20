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
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

/**
 * <b>Purpose:</b> Simple Tree implementation.
 *
 * @author Marc L. Veary
 * @since 1.0
 * @param <T> type of object
 */
public final class TreeNode<T> implements Iterable<TreeNode<T>> {

    /**
     * The data object associated with this node.
     */
    private final T data;

    /**
     * List of childen to this node.
     */
    private final List<TreeNode<T>> children;

    /**
     * List of TreeNode<T> objects in insertion order. In the root node, this member contains all
     * the nodes in the tree in their insertion order.
     */
    private final List<TreeNode<T>> searchIndex;

    /**
     * The parent node for this node.
     */
    private TreeNode<T> parent;

    /**
     * Constructor.
     *
     * @param data object of type {@code T}
     */
    public TreeNode(T data) {
        this.data = Objects.requireNonNull(data, "Parameter 'data' cannot be null.");
        this.children = new ArrayList<>();
        this.searchIndex = new ArrayList<>();
        this.searchIndex.add(this);
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
        this.children.add(childNode);
        addChildToSearchIndex(childNode);
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
     * @throws NoSuchElementException if this is the root node (i.e. has no parent). Use
     *     {@link #isRoot()} before calling this method.
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

    /**
     * Returns the level of this node. Root is zero (0).
     *
     * @return {@code int} indicating the level of this node
     */
    public int getLevel() {
        if (this.isRoot()) {
            return 0;
        } else {
            return this.parent.getLevel() + 1;
        }
    }

    /**
     * Search from this node for a sub-node whose data fulfils the search criteria.
     *
     * @param search an instance of {@link TreeNodeSearch}
     * @return {@code Optional<TreeNode<T>>}
     */
    public Optional<TreeNode<T>> findNode(TreeNodeSearch<T> search) {
        for (TreeNode<T> element : this.searchIndex) {
            if (search.execute(element.data)) {
                return Optional.of(element);
            }
        }
        return Optional.empty();
    }

    /**
     * Returns the number of nodes under this branch of the tree.
     *
     * @return {@code int} the number of children
     */
    public int size() {
        return this.searchIndex.size();
    }

    @Override
    public Iterator<TreeNode<T>> iterator() {
        return new TreeNodeIterator(this);
    }

    /**
     * Registers the referenced node in the {@link #searchIndex}.
     *
     * @param node the {@code TreeNode<T>} to be added to the index.
     */
    private void addChildToSearchIndex(TreeNode<T> node) {
        this.searchIndex.add(node);
        if (this.parent != null) {
            this.parent.addChildToSearchIndex(node);
        }
    }

    /**
     * SAM type interface for use with a <i>lambda</i> expression.
     *
     * <p><b>Usage example:</b>
     *
     * <pre>
     * Optional<TreeNode<String>> result = root.findNode(element -> {
     *     if (element.equals("Test String")) {
     *         return true;
     *     }
     *     return false;
     * });
     * </pre>
     *
     * <p>The above will search from the {@code root} of the tree looking for node whose data
     * {@code equals("Test String")}
     *
     * @author Marc L. Veary
     * @since 1.0
     * @param <T>
     * @see TreeNode#findNode(TreeNodeSearch)
     */
    public interface TreeNodeSearch<T> {

        /**
         * Execute a search for a node.
         *
         * @param data the node's data
         * @return {@code true} if a match is found, otherwise {@code false}
         */
        boolean execute(T data);
    }

    /**
     * <b>Purpose:</b> Private inner class implementing {@link Iterator}.
     *
     * @author Marc L. Veary
     * @since 1.0
     */
    private class TreeNodeIterator implements Iterator<TreeNode<T>> {

        /**
         * LIFO stack of Iterators to process
         */
        private final Deque<Iterator<TreeNode<T>>> iteratorStack;

        /**
         * The parent node from which iteration starts.
         */
        private final TreeNode<T> parent;

        /**
         * Marker indicating if the parent node has been processed.
         */
        private boolean processedParent = false;

        /**
         * Private constructor.
         *
         * @param parent {@code TreeNode<T>}
         */
        private TreeNodeIterator(TreeNode<T> parent) {
            this.parent = Objects.requireNonNull(parent, "Parameter 'parent' cannot be null.");
            this.iteratorStack = new LinkedList<>();

        }

        @Override
        public boolean hasNext() {

            if (!this.processedParent) {
                return true;
            }

            if (this.iteratorStack.isEmpty()) {
                return false;
            }

            return this.iteratorStack.getFirst().hasNext();
        }

        @Override
        public TreeNode<T> next() {

            if (!this.processedParent) {
                this.processedParent = true;
                this.iteratorStack.addFirst(this.parent.children.iterator());
                return this.parent;
            }

            Iterator<TreeNode<T>> it = this.iteratorStack.removeFirst();

            if (it.hasNext()) {
                TreeNode<T> next = it.next();

                if (it.hasNext()) {
                    this.iteratorStack.addFirst(it);
                }

                if (!next.children.isEmpty()) {
                    this.iteratorStack.addFirst(next.children.iterator());
                }

                return next;
            }

            throw new NoSuchElementException();
        }
    }
}
