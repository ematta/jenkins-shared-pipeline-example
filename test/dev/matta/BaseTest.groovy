package dev.matta


import dev.matta.steps.ContextRegistry
import dev.matta.steps.IContext
import dev.matta.steps.IStepExecutor
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class BaseTest extends GroovyTestCase {

    def IContext _context
    def IStepExecutor _steps

    @BeforeEach
    void setUp() {
        _context = mock(IContext.class)
        _steps = mock(IStepExecutor.class)
        when(_context.getStepExecutor()).thenReturn(_steps)
        ContextRegistry.registerContext(_context)
    }

    @AfterEach
    void tearDown() {
    }
}
