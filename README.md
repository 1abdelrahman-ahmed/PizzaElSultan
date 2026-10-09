# Pizza El Sultan — Architecture & SOLID Principles

## 1. Briefly Describe Your Architecture

Pizza El Sultan is a console-based application designed using object-oriented programming and SOLID principles. The system separates responsibilities into different classes and uses interfaces to reduce coupling between components.

The main parts of the architecture are:

- **Product Management:** `Item` is an abstract parent class for `Pizza`, `Drink`, and `GarlicBread`. `Menu` stores products through `List<Item>`, allowing different product types to be handled uniformly.
- **Order Management:** `Order` manages the customer, order items, status, fulfillment method, discount policy, and payment process. `OrderItem` represents a product and its quantity.
- **Pricing:** `DiscountPolicy` defines the discount calculation contract. `OpeningPromotion` and `SpecialPromotion` implement different promotion rules.
- **Payment:** `Payment` defines the payment operation. `Cash`, `Card`, and `OnlineWallet` provide different payment implementations. `ElectronicRefund` separates electronic refund capabilities from ordinary payment operations.
- **Order Status:** `OrderStatus` defines the status behavior, implemented by `NewStatus`, `PreparingStatus`, `ReadyStatus`, `CompletedStatus`, and `CancelledStatus`.
- **Notifications:** `Notification` defines the notification contract, implemented by `SMSNotification` and `WhatsAppNotification`.
- **Fulfillment and Receipts:** `Fulfillment` abstracts takeaway and delivery options, while `Receipt` is responsible for displaying order information.

For example, `Order` depends on the `Payment` interface rather than directly depending on `Cash` or `Card`. This allows the payment method to be changed without rewriting the normal order workflow.

## 2. Give One Example Where You Applied SRP

The Single Responsibility Principle states that a class should have one primary responsibility and one main reason to change.

A clear example is the separation between `Order` and `Receipt`.

- `Order` manages order items, calculates the subtotal and final total, applies the selected discount policy, and handles payment and status changes.
- `Receipt` displays the information associated with an order.

The `Receipt` class receives an `Order` object through its `print(Order order)` method and reads the relevant information from it. It does not independently implement the order's pricing rules.

This separation means that changing the receipt format should not require changing the order-management logic.

## 3. How Can a New Feature or Type Be Added Without Heavily Modifying Existing Code?

We use interfaces, abstraction, and polymorphism to support extension.

For example, when adding `GarlicBread`, we created a new class that extends `Item`. Since `Menu` stores `Item` objects and `OrderItem` works with the same abstraction, garlic bread can be added to menus and orders without introducing special garlic-bread logic into the normal pricing workflow.

Similarly, a new promotion can implement `DiscountPolicy`, and a new payment method can implement `Payment`.

The normal order workflow can continue using these abstractions rather than checking the concrete type of every implementation.

This follows the Open/Closed Principle: the system should be open to extension but closed to unnecessary modification.

## 4. Did You Use Inheritance? If Yes, Explain Why the Child Can Genuinely Substitute for Its Parent.

Yes. We used inheritance for product types.

`Pizza`, `Drink`, and `GarlicBread` inherit from the abstract class `Item`.

Each child represents a genuine kind of menu item and provides the information expected from an `Item`, such as its name, price, and availability.

For example, `GarlicBread` does not need a pizza size or toppings. It can still be used anywhere the system expects an `Item`, because order management and pricing operate on the common product abstraction rather than requiring pizza-specific behavior.

This supports the Liskov Substitution Principle.

We also use interface implementation for interchangeable behaviors, such as `Payment`, `Notification`, and `DiscountPolicy`.

## 5. Did Every Implementation Actually Need Every Method Defined by Its Interface?

We designed interfaces around the capabilities required by their implementations.

For example, `Cash`, `Card`, and `OnlineWallet` implement `Payment`, which defines the `pay(double amount)` operation. All three payment methods need to support payment.

However, cash refunds are handled manually by the cashier. Therefore, we did not put an electronic `refund()` method directly in `Payment`.

Instead, we introduced a separate `ElectronicRefund` interface, implemented by `Card` and `OnlineWallet` only.

This prevents `Cash` from being forced to implement an electronic refund operation that it does not support.

This design follows the Interface Segregation Principle.

Similarly, `Notification` exposes the common `send(String message)` operation, which is appropriate for both SMS and WhatsApp implementations.

## 6. Which High-Level Parts of Your System Depend on Abstractions Rather Than Concrete Implementations?

Several high-level components depend on abstractions:

- **Order → Payment:** `Order` stores a `Payment` reference, allowing payment methods to be substituted without changing the normal payment workflow.
- **Order → DiscountPolicy:** `Order` delegates discount calculation to a `DiscountPolicy` implementation instead of embedding every promotion rule in the order class.
- **CustomerNotifier → Notification:** The notifier depends on the `Notification` interface rather than directly depending on `SMSNotification` or `WhatsAppNotification`.
- **Menu → Item:** `Menu` stores products using the common `Item` abstraction.
- **Order → Fulfillment:** `Order` uses the fulfillment abstraction instead of embedding takeaway and delivery charge calculations directly into its core logic.

These relationships reduce coupling and make the system easier to extend and test.

## 7. Where Did You Choose Composition Instead of Inheritance, and Why?

We used composition in several important places.

First, `Order` contains multiple `OrderItem` objects. Each `OrderItem` references an `Item` and stores its quantity. This is more appropriate than making an order inherit from a product, because an order *contains* products rather than *being* a product.

Second, `Order` holds references to its selected `Payment`, `DiscountPolicy`, `OrderStatus`, and `Fulfillment` objects. These objects provide different behaviors that the order coordinates.

Third, `CustomerNotifier` contains a `Notification` reference. This allows the notification implementation to be selected without creating separate notifier subclasses for every channel.

Composition allows the system to combine behaviors at runtime without creating unnecessary inheritance hierarchies.

## 8. Which Phase 2 Requirement Caused the Largest Change to Your Original Architecture?

The promotion requirement caused an important design change.

The original discount interface accepted only a subtotal. However, the new `SpecialPromotion` requires two conditions:

1. The subtotal must be greater than 300 EGP.
2. The order must contain at least two pizzas.

To support this requirement, we changed `DiscountPolicy` to receive the `Order` object. The promotion can then access both the subtotal and the order items to determine the pizza count.

This allowed us to add `SpecialPromotion` while keeping the normal discount workflow inside `Order`.

The change also introduced a trade-off: passing the entire `Order` to a pricing policy creates additional coupling between pricing and order management. A dedicated pricing context could be a better choice in a larger system.

## 9. What Changed Between Your Initial UML and Final UML?

The final UML reflects the additional requirements introduced during the assignment.

The main changes include:

- **Product extension:** Added `GarlicBread` as a subclass of `Item`, allowing a non-pizza product to participate in the existing menu and order workflow.
- **Promotion extension:** Added `SpecialPromotion` alongside `OpeningPromotion`, both implementing `DiscountPolicy`. The interface now receives an `Order` to support promotion rules that depend on order contents.
- **Payment extension:** Added `OnlineWallet` as another implementation of `Payment`.
- **Refund separation:** Added `ElectronicRefund`, implemented by `Card` and `OnlineWallet`, but not by `Cash`.
- **Notification extension:** Added `WhatsAppNotification` alongside `SMSNotification`, with both implementing `Notification`.
- **Order payment state:** Included `PaymentResult` in the order's payment-related state so that the system can determine whether the latest payment attempt succeeded.

The final UML also makes the abstraction relationships more explicit, including interface implementations and composition relationships.

The Sultan Express requirement was not included in the final design because it was intentionally skipped.

## 10. If Pizza El Sultan Requested Five Additional Payment Methods or Notification Channels Tomorrow, Approximately How Much Existing Code Would Need Modification?

Ideally, very little existing business logic would need modification.

For five new payment methods, we would create five new classes implementing `Payment`. If some methods support electronic refunds, those classes would also implement `ElectronicRefund`.

For five new notification channels, we would create five new classes implementing `Notification`.

The existing `Order` and `CustomerNotifier` workflows would not need to change, provided the new implementations follow the existing contracts and can be configured through the application's composition or setup code.

The main changes would be adding the new classes and registering or selecting them where the application is configured.

This is an example of the Open/Closed Principle. It does not mean that absolutely no existing code can ever change; configuration, integration, or new business rules may still require modifications.

## 11. Which Part of Your Architecture Are You Least Satisfied With, and Why?

The part I am least satisfied with is the dependency between `DiscountPolicy` and the complete `Order` object.

Passing `Order` to the discount policy makes it easy to implement promotions that depend on multiple order properties. However, it also gives pricing policies access to more information than they actually need and couples the pricing layer to the order-management layer.

For example, `SpecialPromotion` only needs the subtotal and the number of pizzas, not payment information or order-status transitions.

A better design for a larger system would introduce a dedicated `DiscountContext` containing only the information required for discount calculation.

This would make the pricing logic more focused, easier to test independently, and less dependent on changes to the `Order` class.

## 12. If You Restarted the Assignment Today, What Would You Design Differently?

If I restarted the assignment, I would keep the overall architecture but improve several decisions:

1. **Introduce a discount context:** Instead of passing the complete `Order` to `DiscountPolicy`, I would pass a dedicated object containing the subtotal, pizza count, and other pricing information required by promotions.
2. **Separate payment processing from order management further:** I would consider a dedicated payment service to handle payment attempts, payment results, and refund rules, especially if real payment providers were introduced.
3. **Strengthen order-state rules:** I would ensure that status transitions are validated centrally, including whether an order can be completed before successful payment and whether repeated payment attempts are allowed.
4. **Make monetary calculations safer:** I would use `BigDecimal` instead of `double` for monetary values to avoid floating-point precision issues.
5. **Improve automated testing:** I would add unit tests for pricing policies, payment failures, unavailable items, invalid quantities, delivery validation, refunds, and terminal order states.
6. **Separate configuration from demonstration code:** I would keep `Main` focused on creating dependencies and demonstrating application behavior, while moving reusable business logic into dedicated classes.

These changes would improve maintainability, testability, and correctness without discarding the existing object-oriented design.