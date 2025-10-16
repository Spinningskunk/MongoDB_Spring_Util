package com.ikun.cm.transactional;

import com.mongodb.client.ClientSession;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author: HeKun
 * @date: 2025/10/16 15:10
 * @description: 用ThreadLocal管理事务嵌套
 */
public class MongoTransactionContext {
    private static final ThreadLocal<Deque<ClientSession>> SESSION_STACK =
            ThreadLocal.withInitial(ArrayDeque::new);


    public static void push(ClientSession session) {
        SESSION_STACK.get().push(session);
    }

    public static ClientSession peek() {
        return SESSION_STACK.get().peek();
    }

    public static ClientSession pop() {
        return SESSION_STACK.get().pop();
    }

    public static boolean hasSession() {
        return !SESSION_STACK.get().isEmpty();
    }

    public static void clear() {
        SESSION_STACK.remove();
    }


}
