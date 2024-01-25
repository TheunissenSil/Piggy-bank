describe('User Login and Overview Display', () => {
    it('successfully logs in and displays the overview page', () => {
        cy.visit('http://localhost:3000/login');
        cy.contains('Melvin').click();
        cy.contains('Transacties').click();

        cy.url().should('include', '/transactions');
        cy.get('div.transaction').should('have.length', 4); 
    });
});
