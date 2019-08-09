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

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 *
 * @author Marc L. Veary
 * @since 1.0
 * @param <T>
 */
public final class TreeNodeIterator<T> implements Iterator<TreeNode<T>> {

    private final Stack<TreeNode<T>> stack;
    private TreeNode<T> next;

    /**
     * Constructor.
     *
     * @param parent {@code TreeNode<T>}
     */
    public TreeNodeIterator(TreeNode<T> parent) {
        this.stack = new Stack<TreeNode<T>>();
        this.stack.push(parent);
    }

    @Override
    public boolean hasNext() {
        boolean retval = false;
        this.next = null;

        if (!this.stack.isEmpty()) {
            this.next = this.stack.pop();
            this.stack.addAll(this.next.getChildren());
            retval = true;
        }

        return retval;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration. Not {@code null}.
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public TreeNode<T> next() {
        if (this.next != null) {
            return this.next;
        }
        throw new NoSuchElementException();
    }
}
