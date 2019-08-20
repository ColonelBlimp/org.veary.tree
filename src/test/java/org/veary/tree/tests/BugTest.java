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

package org.veary.tree.tests;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.veary.tree.TreeNode;

public class BugTest {

    @Test(expectedExceptions = NoSuchElementException.class)
    public void hasNextWhenNextIsNull() {
        TreeNode<String> root = buildTree();

        Iterator<TreeNode<String>> iterator = root.iterator();
        Assert.assertNotNull(iterator);

        for (int i = 0; i < root.size(); i++) {
            Assert.assertTrue(iterator.hasNext());
            Assert.assertNotNull(iterator.next());
        }

        Assert.assertFalse(iterator.hasNext());
        iterator.next();
    }

    @Test
    public void callHasNext() {
        TreeNode<String> root = buildTree();

        Iterator<TreeNode<String>> iterator = root.iterator();
        Assert.assertNotNull(iterator);

        for (int i = 0; i < 20; i++) {
            Assert.assertTrue(iterator.hasNext());
        }
    }

    private TreeNode<String> buildTree() {
        TreeNode<String> root = new TreeNode<>("BALANCE");
        TreeNode<String> netWorth = root.addChild("NET WORTH");
        TreeNode<String> assets = netWorth.addChild("ASSETS");
        assets.addChild("Cash");
        assets.addChild("Bank");
        TreeNode<String> liabilities = netWorth.addChild("LIABILITIES");
        liabilities.addChild("Credit Card");
        liabilities.addChild("Bank Loan");
        liabilities.addChild("Mortgage");
        TreeNode<String> incomeExpenses = root.addChild("INCOME & EXPENSES");
        TreeNode<String> income = incomeExpenses.addChild("INCOME");
        TreeNode<String> expenses = incomeExpenses.addChild("EXPENSES");
        root.addChild("OPENING BALANCE");
        income.addChild("Salary");
        income.addChild("Sales");
        expenses.addChild("Food");

        TreeNode<String> car = expenses.addChild("Car");
        car.addChild("Fuel");

        return root;
    }
}
